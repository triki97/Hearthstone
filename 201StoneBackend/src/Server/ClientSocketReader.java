package Server;

import org.json.simple.JSONObject;

public class ClientSocketReader {	
	public JSONObject parseMessage(String clientMessage) {
		JSONObject response;
		
		String[] tokens = clientMessage.split(Constants.CLIENT_DELIMETER);
		if(tokens.length <= 0) {
			System.out.println("Client Message invalid");
			response = successResponse(false);
		}
		
		response = successResponse(true);
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject successResponse(boolean success){
		JSONObject response = new JSONObject();
		response.put("success", success);
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject successResponseWithUser(boolean success, String username){
		JSONObject response = new JSONObject();
		response.put("success", success);
		if(success) response.put("username", username);
		return response;
	}

}
