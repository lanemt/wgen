run --input wgen_project/wgen_project__xml_sample_data.xml --output fromXML.xml --schema wgen_project/wgen_project__xml_schema.xsd

run --input wgen_project/wgen_project__xml_sample_data.xml --output fromXML.csv --schema wgen_project/wgen_project__xml_schema.xsd

run --input wgen_project/wgen_project__csv_sample_data.csv --output fromCSV.xml --schema wgen_project/wgen_project__xml_schema.xsd

run --input wgen_project/wgen_project__csv_sample_data.csv --output fromCSV.csv --schema wgen_project/wgen_project__xml_schema.xsd

run --input wgen_project/wgen_project__xml_sample_data_bad.xml --output fromBadXML.csv --schema wgen_project/wgen_project__xml_schema.xsd
