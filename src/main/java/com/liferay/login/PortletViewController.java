/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.login;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.springmvc.Customer;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class PortletViewController {
	//Default Render Method

		@RenderMapping
		public String handleRenderRequest(RenderRequest request,RenderResponse response, Model model) {
			return "login";
		}



		//After action This method decide the flow of jsp

		@RenderMapping(params = "action=renderAfterAction")
		public String afterLoginRenderMethod(RenderRequest request,RenderResponse response) {
			PortletSession session=request.getPortletSession();
			//String jspValue = (String) request.getAttribute("jspValue");
			String jspValue = (String) session.getAttribute("jspValue");
			try{if (jspValue.equalsIgnoreCase("fail")) {
				return "login";
			} else {
				return "success";
			}
			}
			catch(Exception e) {
				//e.printStackTrace();
				return "login";
			}
		}

		@RenderMapping(params = "action=showForm")
		public String viewByParameter(RenderRequest request,RenderResponse response) {
			request.getPortletSession().removeAttribute("userName");
			request.getPortletSession().removeAttribute("password");
			request.getPortletSession().removeAttribute("jspValue");
			return "login";
		}

		

		@ActionMapping(params = "action=loginSubmit")

		public void loginSubmit(ActionRequest request, ActionResponse response) {

			String userName = ParamUtil.getString(request, "username", "");

			String password = ParamUtil.getString(request, "password", "");
			PortletSession session=request.getPortletSession();

			if (userName.equalsIgnoreCase(password)) {
				response.setRenderParameter("action", "renderAfterAction"); //This parameter decide which method is called after completion of this method
				request.setAttribute("jspValue", "success"); //Attribute use for which jsp is render
				request.setAttribute("userName", userName); //Attribute fetch on success.jsp
				session.setAttribute("userName", userName);
				session.setAttribute("password", password);
				session.setAttribute("jspValue", "success");
			} else {
				response.setRenderParameter("action", "renderAfterAction");
				request.setAttribute("jspValue", "fail");
			}



		}
}