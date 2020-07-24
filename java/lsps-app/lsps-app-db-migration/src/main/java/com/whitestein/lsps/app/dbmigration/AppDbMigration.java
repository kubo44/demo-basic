package com.whitestein.lsps.app.dbmigration;

import com.whitestein.lsps.dbmigration.DbMigration;

/**
 * DB migration for business tables
 */
public class AppDbMigration extends DbMigration {

	//TODO: Update APP prefix to match prefix of your business tables
	private static final String SCHEMA_VERSION_TABLE = "APP_SCHEMA_VERSION";

	public static void main(String[] args) {
		new AppDbMigration().migrate(args);
	}

	@Override
	protected String getSchemaVersionTableName() {
		return SCHEMA_VERSION_TABLE;
	}
}
