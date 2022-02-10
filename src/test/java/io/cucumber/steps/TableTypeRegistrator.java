package io.cucumber.steps;

import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;
import io.cucumber.table_type.*;


public class TableTypeRegistrator implements TypeRegistryConfigurer {

    @Override
    public void configureTypeRegistry(TypeRegistry registry) {

        registry.defineDataTableType(new DataTableType(Header.class, (TableEntryTransformer<Header>) entry ->
                new Header(entry.get("header"), entry.get("value"))));

        registry.defineDataTableType(new DataTableType(Cookie.class, (TableEntryTransformer<Cookie>) entry ->
                new Cookie(entry.get("cookie"), entry.get("value"))));

        registry.defineDataTableType(new DataTableType(JsonAttribute.class, (TableEntryTransformer<JsonAttribute>) entry ->
                new JsonAttribute(entry.get("path"), entry.get("value"), entry.get("type"))));

        registry.defineDataTableType(new DataTableType(Data.class, (TableEntryTransformer<Data>) entry ->
                new Data(entry.get("left"), entry.get("right"), entry.get("type"))));

        registry.defineDataTableType(new DataTableType(JsonData.class, (TableEntryTransformer<JsonData>) entry ->
                new JsonData(entry.get("left path"), entry.get("right path"), entry.get("type"))));

        registry.defineDataTableType(new DataTableType(DataPlaceholder.class, (TableEntryTransformer<DataPlaceholder>) entry ->
                new DataPlaceholder(entry.get("placeholder"), entry.get("value"))));

        registry.defineDataTableType(new DataTableType(JsonDataPlaceholder.class, (TableEntryTransformer<JsonDataPlaceholder>) entry ->
                new JsonDataPlaceholder(entry.get("placeholder"), entry.get("path"))));

        registry.defineDataTableType(new DataTableType(JsonDataRandomPlaceholder.class, (TableEntryTransformer<JsonDataRandomPlaceholder>) entry ->
                new JsonDataRandomPlaceholder(entry.get("placeholder"), entry.get("length"), entry.get("type"))));

        registry.defineDataTableType(new DataTableType(DatabaseCell.class, (TableEntryTransformer<DatabaseCell>) entry ->
                new DatabaseCell(entry.get("column"), entry.get("value"), Integer.parseInt(entry.get("row")))));

        registry.defineDataTableType(new DataTableType(DatabaseCellPlaceholder.class, (TableEntryTransformer<DatabaseCellPlaceholder>) entry ->
                new DatabaseCellPlaceholder(entry.get("placeholder"), entry.get("column"), Integer.parseInt(entry.get("row")))));
    }
}
