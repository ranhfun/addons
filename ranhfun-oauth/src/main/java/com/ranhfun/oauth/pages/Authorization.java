package com.ranhfun.oauth.pages;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.OAuth;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.slf4j.Logger;

import com.ranhfun.oauth.OAuthRequestToken;
import com.ranhfun.oauth.pages.authorization.Confirm;
import com.ranhfun.oauth.services.HttpOAuthResponse;
import com.ranhfun.oauth.services.OAuthProvider;
import com.ranhfun.oauth.util.OAuthUtils;

public class Authorization {

	@Inject
	private Logger logger;

	@Inject
	private HttpServletRequest req;

	@Inject
	private HttpServletResponse resp;

	@Inject
	private OAuthProvider provider;

	@Inject
	private PageRenderLinkSource pageRenderLinkSource;
	
	private String format;
	
	@Property
	private com.ranhfun.oauth.OAuthConsumer consumer;
	
	@Property
	private OAuthRequestToken requestToken;

	Object onActivate() throws IOException {
		logger.debug("Consumer token authorization request");

		try {
			String[] values = req.getParameterValues(OAuth.OAUTH_TOKEN);
			if (values == null || values.length != 1) {
				resp.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
				return new HttpOAuthResponse(resp);
			}
			String requestTokenKey = values[0];

			requestToken = provider.getRequestToken(null, requestTokenKey);
			consumer = requestToken.getConsumer();

			String acceptHeader = req.getHeader("Accept");
			format = acceptHeader == null
					|| acceptHeader.startsWith("application/xml") ? "xml"
					: "html";

			requestEndUserConfirmation(req, resp, consumer, requestToken,
					format);

		} catch (Exception x) {
			logger.error("Exception ", x);
			OAuthUtils.makeErrorResponse(resp, x.getMessage(),
					HttpURLConnection.HTTP_INTERNAL_ERROR, provider);
		}
		if (format.equals("xml")) {
			return new HttpOAuthResponse(resp);
		}
		return null;
	}

	private void requestEndUserConfirmation(HttpServletRequest req,
			HttpServletResponse resp,
			com.ranhfun.oauth.OAuthConsumer consumer,
			OAuthRequestToken requestToken, String format) {

		if ("xml".equals(format)) {
			String uri = getAuthorizationConfirmURI(requestToken.getToken());
			StringBuilder sb = new StringBuilder();
			sb.append(
					"<tokenAuthorizationRequest xmlns=\"http://org.jboss.com/resteasy/oauth\" ")
					.append("replyTo=\"").append(uri).append("\">");
			sb.append("<consumerId>").append(consumer.getKey())
					.append("</consumerId>");
			if (consumer.getDisplayName() != null) {
				sb.append("<consumerName>").append(consumer.getDisplayName())
						.append("</consumerName>");
			}
			if (requestToken.getScopes() != null) {
				sb.append("<scopes>").append(requestToken.getScopes()[0])
						.append("</scopes>");
			}
			if (requestToken.getPermissions() != null) {
				sb.append("<permissions>")
						.append(requestToken.getPermissions()[0])
						.append("</permissions>");
			}
			sb.append("</tokenAuthorizationRequest>");
			try {
				resp.getWriter().append(sb.toString());
				resp.setStatus(HttpURLConnection.HTTP_OK);
			} catch (IOException ex) {
				resp.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
			}
		}
	}
	
	public String getAuthorizationConfirmURI(String tokenKey) {
        Link link = pageRenderLinkSource.createPageRenderLink(Confirm.class);
        link.addParameter(OAuth.OAUTH_TOKEN, OAuthUtils.encodeForOAuth(tokenKey));
        return link.toAbsoluteURI();
	}
	
	public String getConfirmURI() {
		return getAuthorizationConfirmURI(requestToken.getToken());
	}
	
	public String getScope() {
		return consumer.getScopes()!=null && consumer.getScopes().length>0 ? consumer.getScopes()[0] : "";
	}
	
	public String getPermission() {
		return consumer.getPermissions()!=null && consumer.getPermissions().length>0 ? consumer.getPermissions()[0] : "";
	}
}
