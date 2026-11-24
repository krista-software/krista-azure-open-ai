/*
 * Azure Open Ai Extension for Krista
 * Copyright (C) 2025 Krista Software
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package app.krista.extensions.catalog;

import app.krista.extension.impl.anno.Attribute;
import app.krista.extension.impl.anno.CatalogRequest;
import app.krista.extension.impl.anno.Domain;
import app.krista.extension.impl.anno.Field;
import app.krista.extensions.impl.QueryImpl;

import javax.inject.Inject;

@Domain(id = "catEntryDomain_6568c455-2416-4fcf-8d7a-2f3e0bef059b",
        name = "LLMs",
        ecosystemId = "catEntryEcosystem_d3b05047-07b0-4b06-95a3-9fb8f7f608d9",
        ecosystemName = "Krista",
        ecosystemVersion = "2e2aa098-8c84-42b8-9e0b-64c7e9f64a3c")
public class QueryArea {

    private final QueryImpl query;

    @Inject
    public QueryArea(QueryImpl query) {
        this.query = query;
    }

    @CatalogRequest(
            id = "localDomainRequest_ead52dea-aabc-4d65-abab-8f23284dece2",
            name = "Ask LLM",
            description = "Queries a LLM with the given prompt",
            area = "Query",
            type = CatalogRequest.Type.QUERY_SYSTEM)
    @Field.Text(name = "LLM Response", required = false, attributes = {
            @Attribute(name = "visualWidth", value = "S")}, options = {})
    public String askLLM(
            @Field.Text(name = "Prompt", required = true, attributes = {
                    @Attribute(name = "visualWidth", value = "S")}, options = {}) String prompt) {
        return query.queryModel(prompt);
    }

    @CatalogRequest(
            id = "localDomainRequest_1059f8f1-ee8a-4183-a0cb-e94e40f47a57",
            name = "Get AI Model name",
            description = "Returns the name of the AI model that is being queried",
            area = "Query",
            type = CatalogRequest.Type.QUERY_SYSTEM)
    @Field.Text(name = "AI Model", required = false, attributes = {
            @Attribute(name = "visualWidth", value = "S")}, options = {})
    public String getAIModelName() {
        return query.getAIModel();
    }

}