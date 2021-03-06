package org.drip.controller;

import org.drip.model.Customer;
import org.drip.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/accounts")
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getAccounts(Model model) {
		Boolean isAuthenticated = !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
		if (isAuthenticated) {
			Customer customer = (Customer) (SecurityContextHolder.getContext().getAuthentication());
			model.addAttribute("accounts", customer.getId());
			return "accounts";
		} else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/payments", method = RequestMethod.GET)
	public String getAccountPayments(Model model) {
		Boolean isAuthenticated = !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
		if (isAuthenticated) {
			Customer customer = (Customer) (SecurityContextHolder.getContext().getAuthentication());
			model.addAttribute("payments", accountService.getPaymentsByCustomer(customer.getId()));
			return "payments";
		} else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value="/{accountNumber}/payments", method = RequestMethod.GET)
	public String getAccountPaymentsByAccountNumber(@PathVariable String accountNumber, Model model) {
		Boolean isAuthenticated = !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
		if (isAuthenticated) {
			model.addAttribute("accountPayments", accountService.getPayments(accountNumber));
			return "account-payments";
		} else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/{accountNumber}/bills", method = RequestMethod.GET)
	public String getBillSummaries(@PathVariable String accountNumber, Model model) {
		Boolean isAuthenticated = !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
		if (isAuthenticated) {
			model.addAttribute("billSummaries", accountService.getBillSummaries(accountNumber));
			return "bill-history";
		} else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/bills", method = RequestMethod.GET)
	public String getAllBillSummaries(Model model) {
		Boolean isAuthenticated = !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
		if (isAuthenticated) {
			Customer customer = (Customer)SecurityContextHolder.getContext().getAuthentication();
			model.addAttribute("billSummaries", accountService.getBillSummaries(customer.getId()));
			return "bill-history";
		} else {
			return "redirect:/login";
		}
	}	
}
