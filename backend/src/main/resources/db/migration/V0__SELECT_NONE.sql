/**
  This is a Flyway migration script.

  File naming:
    Prefix the file name with "V" for a single run migration.
    Prefix the file name with "R" for re-runnable scripts (EG: views).

    Follow the prefix with a number (incremented by one from the previous version)
    The number must then be followed by two underscores and a description of what the migration does.
    Finally, ensure the file extension is ".sql"

    As an example, the next file would be called something along the lines of "V2__Description.sql"
 */
SELECT 1
FROM DUAL;