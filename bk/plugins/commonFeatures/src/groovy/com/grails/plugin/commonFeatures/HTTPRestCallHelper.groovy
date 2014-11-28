package com.grails.plugin.commonFeatures

import groovy.util.logging.Log4j

@Log4j
class HTTPRestCallHelper {
    public static HTTPResponseDTO postJSONToUrlAndReturnResponse(String urlString, String json) {
        HttpURLConnection conn = null
        HTTPResponseDTO responseDTO = null
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            responseDTO = processResponse(conn)
        } catch (MalformedURLException e) {
            log.error(e.cause + e.message)
        } catch (IOException e) {
            log.error(e.cause + e.message)
        } catch (Exception e) {
            log.error(e.cause + e.message)
        } finally {
            conn?.disconnect();
        }
        return responseDTO
    }

    public static HTTPResponseDTO getJSONFromUrl(String urlString) {
        HttpURLConnection conn = null
        HTTPResponseDTO responseDTO = null
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            responseDTO = processResponse(conn)
        } catch (MalformedURLException e) {
            log.error(e.cause + e.message)
        } catch (IOException e) {
            log.error(e.cause + e.message)
        } finally {
            conn.disconnect();
        }
        return responseDTO
    }

    public static HTTPResponseDTO deleteRequest(String urlString) {
        HttpURLConnection conn = null
        HTTPResponseDTO responseDTO = null
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            processResponse(conn)
        } catch (MalformedURLException e) {
            log.error(e.cause + e.message)
        } catch (IOException e) {
            log.error(e.cause + e.message)
        } catch (Exception e) {
            log.error(e.cause + e.message)
        } finally {
            conn?.disconnect();
        }
        return responseDTO
    }

    private static HTTPResponseDTO processResponse(HttpURLConnection conn) {
        StringBuilder output = null
        BufferedReader br = null
        if (conn.responseCode >= 400) {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))
        } else {
            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        }
        String line = null;
        output = new StringBuilder()
        while ((line = br.readLine()) != null) {
            output.append(line);
        }
        return new HTTPResponseDTO(responseCode: conn.responseCode, responseContent: output.toString())
    }
}
