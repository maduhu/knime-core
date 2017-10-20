package org.knime.openapi;

import java.io.File;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.dialog.ExternalNodeData;
import org.knime.core.node.workflow.WorkflowLoadHelper;
import org.knime.core.node.workflow.WorkflowManager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class WorkflowAnalyzer implements IApplication {
	public JsonNode createOpenAPI(WorkflowManager wfm) {
		JsonNodeFactory f = JsonNodeFactory.instance;
		
		ObjectNode root = f.objectNode();
		
		ObjectNode properties = f.objectNode();
		root.set("properties", properties);
		
		for (Map.Entry<String, ExternalNodeData> e : wfm.getInputNodes().entrySet()) {
			if (e.getValue().getJSONValue() != null) {
				ObjectNode input = translateToSchema(e.getValue().getJSONValue(), f);
				properties.set(e.getKey(), input);
				e.getValue().getDescription().ifPresent(d -> input.put("description", d));
			}
		}
		
		return root;
	}

	
	private ObjectNode translateToSchema(JsonValue v, JsonNodeFactory f) {
		ObjectNode node = f.objectNode();
		
		if (v instanceof JsonObject) {
			node.put("type", "object");
			
			ObjectNode properties = f.objectNode();
			for (Map.Entry<String, JsonValue> e : ((JsonObject) v).entrySet()) {
				ObjectNode child = translateToSchema(e.getValue(), f);
				properties.set(e.getKey(), child);
			}
			
			node.set("properties", properties);
		} else if (v instanceof JsonNumber) {
			JsonNumber number = (JsonNumber) v;
			
			if (number.isIntegral()) {
				node.put("type", "integer");
				node.put("default", number.longValue());					
			} else {
				node.put("type", "number");
				node.put("default", number.doubleValue());
			}
		} else if (v instanceof JsonString) {
			node.put("type", "string");
			node.put("default", ((JsonString) v).getString());									
		} else if ((v.getValueType() == ValueType.FALSE) ||
				(v.getValueType() == ValueType.TRUE)) {
			node.put("type", "boolean");
			node.put("default", v.toString());													
		} else if (v instanceof JsonArray) {
			node.put("type", "array");
			if (!((JsonArray)v).isEmpty()) {
				ObjectNode child = translateToSchema(((JsonArray)v).get(0), f);
				node.set("items", child);
			}
		}
		
		return node;
	}
	
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);

		File workflowDir = new File(args[0]);
		WorkflowLoadHelper lh = new WorkflowLoadHelper(workflowDir);
		WorkflowManager wfm = WorkflowManager.loadProject(workflowDir, new ExecutionMonitor(), lh).getWorkflowManager();

		JsonNode api = createOpenAPI(wfm);
		ObjectMapper mapper = new ObjectMapper();
		mapper.writer().withDefaultPrettyPrinter().writeValue(
				new File("/home/thor/workspace/KNIME-trunk/git/knime-core/org.knime.openapi/web/input-parameters.json"),
				api);

		mapper.writer().withDefaultPrettyPrinter().writeValue(System.out, api);
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
	}
}
