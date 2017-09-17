package com.upwork.tkachenko.module.account.dao.impl;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.upwork.tkachenko.module.account.exception.DataAccessException;
import com.upwork.tkachenko.module.account.exception.DuplicatedCustomerNameException;
import com.upwork.tkachenko.module.account.exception.RequestValidationException;
import com.upwork.tkachenko.module.account.model.Account;
import com.upwork.tkachenko.module.common.util.Preconditions;

@Component
public class JsonFileClientImpl implements JsonFileClient {

	private Lock lock = new ReentrantLock();

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonFileClientImpl.class);

	private static final String DB_ROOT = "db/";
	private static final String METADATA_FILE_PATH = DB_ROOT + "metadata.json";
	private static final String DATA_FILE_PATH = DB_ROOT + "data.json";

	private final ObjectMapper mapper = new ObjectMapper();

	private File metadataFile;

	private File dataFile;

	public JsonFileClientImpl() {
		this.metadataFile = new File(METADATA_FILE_PATH);
		this.dataFile = new File(DATA_FILE_PATH);

		init();
	}

	private void init() {

		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		try {
			if (!metadataFile.exists() || (metadataFile.exists()
					&& StringUtils.isEmpty(FileUtils.readFileToString(metadataFile)))) {
				flushMetadata();
			}
		} catch (Exception e) {
			try {
				flushMetadata();
			} catch (Exception ee) {
				LOGGER.error("Unable to save metadata json file");
				System.exit(-1);
			}
		}

		try {
			if (!dataFile.exists()) {
				dataFile.createNewFile();
			}
		} catch (Exception e) {
			LOGGER.error("Unable to create json data file");
			System.exit(-1);
		}

	}

	private void flushMetadata() throws JsonGenerationException, IOException {

		File db = new File(DB_ROOT);
		if (!db.exists()) {
			db.mkdir();
		}

		JsonSchemaGenerator generator = new JsonSchemaGenerator(mapper);
		JsonSchema jsonSchema = generator.generateSchema(Account.class);

		mapper.writeValue(metadataFile, jsonSchema);

		LOGGER.info("Metadata file created at: " + metadataFile.getAbsolutePath());
	}

	public List<Account> getRecords() throws DataAccessException {
		try {
			if (StringUtils.isEmpty(FileUtils.readFileToString(dataFile))) {
				return new LinkedList<Account>();
			}
			return mapper.readValue(dataFile, new TypeReference<List<Account>>() {
			});
		} catch (IOException e) {
			throw new DataAccessException("Unable get data from storage");
		}
	}

	@Override
	public Account addRecord(Account account)
			throws DataAccessException, DuplicatedCustomerNameException {
		try {
			lock.lock();
			validateCustomerName(account);
			account.setAccountId(UUID.randomUUID().toString());
			List<Account> data = getRecords();
			data.add(account);
			mapper.writeValue(dataFile, data);
		} catch (DataAccessException | IOException e) {
			throw new DataAccessException("Unable to save data to storage");
		} finally {
			lock.unlock();
		}

		return account;
	}

	private void validateCustomerName(Account account)
			throws DataAccessException, DuplicatedCustomerNameException {
		boolean compareAndSet = getRecords().stream()
				.filter(a -> a.getCustomerName().trim().equalsIgnoreCase(account.getCustomerName()))
				.findFirst().isPresent();

		if (compareAndSet) {
			throw new DuplicatedCustomerNameException(
					"Duplicated customer name: " + account.getCustomerName());
		}

	}

	@Override
	public Account modifyRecord(Account account, String jsonPatch)
			throws DataAccessException, RequestValidationException {
		try {
			lock.lock();
			JsonPatch patch = JsonPatch.fromJson(mapper.readValue(jsonPatch, JsonNode.class));
			JsonNode result = patch
					.apply(mapper.readValue(mapper.writeValueAsString(account), JsonNode.class));

			Account modifiedAccount = mapper.readValue(result.toString(), Account.class);

			if (!account.getAccountId().equalsIgnoreCase(modifiedAccount.getAccountId())) {
				throw new RequestValidationException("Unable to modify accountId");
			}

			Preconditions.checkCurrency(modifiedAccount.getCurrency());

			List<Account> realData = getRecords();
			realData.removeIf(a -> account.getAccountId().equalsIgnoreCase(a.getAccountId()));
			realData.add(modifiedAccount);
			mapper.writeValue(dataFile, realData);

			return modifiedAccount;

		} catch (IOException | JsonPatchException e) {
			throw new DataAccessException("Unable to apply patch: " + e.getMessage());
		} finally {
			lock.unlock();
		}
	}
}
