package auction.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import auction.dto.OrderObjectDTO;
import auction.model.Firm;
import auction.model.FirmOrder;
import auction.model.OrderGoods;
import auction.spring.service.FirmService;
import auction.spring.service.OrderGoodsService;
import auction.user.User;
import auction.user.UserService;

@Component
public class ChangeOrderService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	FirmService firmService;
	
	@Autowired
	OrderGoodsService orderGoodsService;
	
	@Autowired
	RuntimeService runtimeService;
	
	//${changeOrderService.noOrders(initiator)}
	
	public void noOrders(String username) {
		System.out.println("usao u no orders");
		User user = userService.findOneByUsername(username);
		System.out.println("korsnik " + username);
		
	}
	
	public void extendDeadline(String username) {
		System.out.println("usao u extend deadline");
		User user = userService.findOneByUsername(username);
		System.out.println("korsnik " + username);
	}
	
	public OrderObjectDTO sendToNewFirms(OrderObjectDTO orderObjectDTO, Date emptyNewDeadline) {
		System.out.println(orderObjectDTO);
		
		List<Firm> newFirmList = getNewFirms(orderObjectDTO);
		List<Firm> firmList = new ArrayList<Firm>();

		int i = 0; 
		if(newFirmList.size() >= orderObjectDTO.getOrderGoods().getExpectedBids().intValue()) {
			for(Firm firm : newFirmList) {
				if(i < orderObjectDTO.getOrderGoods().getExpectedBids().intValue()) {
					List<OrderGoods> orderList = firm.getOrderGoods();
					orderList.add(orderObjectDTO.getOrderGoods());
					firm.setOrderGoods(orderList);
					System.out.println("Lista ordera " + firm.getOrderGoods().size());
					firmService.save(firm);
					firmList.add(firm);
					i++;
				} else {
					break;
				}
			}
		} else {
			OrderObjectDTO orderDTO = new OrderObjectDTO();
			orderDTO.setFirms(firmList);
			OrderGoods orderGoods = orderObjectDTO.getOrderGoods();
			orderGoods.setReceiveDeadline(emptyNewDeadline);
			List<Firm> getFirms = orderObjectDTO.getFirms();
			List<Firm> newFirms = new ArrayList<Firm>();
			for(Firm firm : newFirmList) {
				getFirms.add(firm);
				newFirms.add(firm);
			}
			//getFirms.addAll(newFirmList);
			orderGoods.setFirm(getFirms);
			orderGoods = orderGoodsService.save(orderGoods);
			orderDTO.setOrderGoods(orderGoods);
			orderDTO.setFirms(newFirms);
			
			return orderDTO;
		}
		
		System.out.println("poslati novim firmana, br firmi " + firmList.size());
		OrderObjectDTO orderDTO = new OrderObjectDTO();
		orderDTO.setFirms(firmList);
		OrderGoods orderGoods = orderObjectDTO.getOrderGoods();
		orderGoods.setReceiveDeadline(emptyNewDeadline);
		orderGoods = orderGoodsService.save(orderGoods);
		orderDTO.setOrderGoods(orderGoods);
		return orderDTO;
		
	}
	
	public OrderObjectDTO changeFirms(OrderObjectDTO orderObjectDTO, Date newDeadline, String executionId) {
		
		
		List<Firm> newFirmList = getNewFirms(orderObjectDTO);
		List<Firm> firmList = new ArrayList<Firm>();
		/*for(FirmOrder firmOrder : firmOrderList) {
			firmList.add(firmOrder.getFirm());
		}*/
		int i = 0;
		if(newFirmList.size() >= orderObjectDTO.getOrderGoods().getExpectedBids().intValue()) {
			for(Firm firm : newFirmList) {
				if(i < orderObjectDTO.getOrderGoods().getExpectedBids().intValue()) {
					List<OrderGoods> orderList = firm.getOrderGoods();
					orderList.add(orderObjectDTO.getOrderGoods());
					firm.setOrderGoods(orderList);
					System.out.println("Lista ordera " + firm.getOrderGoods().size());
					firmService.save(firm);
					firmList.add(firm);
					i++;
				} else {
					break;
				}
			}
		} else {
			OrderObjectDTO orderDTO = new OrderObjectDTO();
			orderDTO.setFirms(firmList);
			OrderGoods orderGoods = orderObjectDTO.getOrderGoods();
			orderGoods.setReceiveDeadline(newDeadline);
			List<Firm> getFirms = orderObjectDTO.getFirms();
			List<Firm> newFirms = new ArrayList<Firm>();
			for(Firm firm : newFirmList) {
				getFirms.add(firm);
				newFirms.add(firm);
			}
			//getFirms.addAll(newFirmList);
			orderGoods.setFirm(getFirms);
			orderGoods = orderGoodsService.save(orderGoods);
			orderDTO.setOrderGoods(orderGoods);
			orderDTO.setFirms(newFirms);
			HashMap<String, Object> variables = (HashMap<String, Object>) runtimeService.getVariables(executionId);
			List<FirmOrder> firmOrderList = new ArrayList<FirmOrder>();
			//firmOrderList.add(firmOrder);
			variables.put("ordersFromFirm", firmOrderList);
			runtimeService.setVariables(executionId, variables);
			return orderDTO;
		}
		
		System.out.println("poslati novim firmana, br firmi " + firmList.size());
		OrderObjectDTO orderDTO = new OrderObjectDTO();
		orderDTO.setFirms(firmList);
		OrderGoods orderGoods = orderObjectDTO.getOrderGoods();
		orderGoods.setReceiveDeadline(newDeadline);
		orderGoods = orderGoodsService.save(orderGoods);
		orderDTO.setOrderGoods(orderGoods);
		HashMap<String, Object> variables = (HashMap<String, Object>) runtimeService.getVariables(executionId);
		List<FirmOrder> firmOrderList = new ArrayList<FirmOrder>();
		//firmOrderList.add(firmOrder);
		variables.put("ordersFromFirm", firmOrderList);
		runtimeService.setVariables(executionId, variables);
		return orderDTO;
	}
	
	private List<Firm> getNewFirms(OrderObjectDTO orderObjectDTO){
		List<Firm> firmList = firmService.findByCategory(orderObjectDTO.getOrderGoods().getCategory());
	//	List<Firm> newFirmList = new ArrayList<Firm>();
		List<Firm> allFirmList = firmService.findByCategory(orderObjectDTO.getOrderGoods().getCategory());
		for(Firm firm : firmList) {
			for(OrderGoods order : firm.getOrderGoods()) {
				if(order.getId().equals(orderObjectDTO.getOrderGoods().getId())) {
					allFirmList.remove(firm);
				}
			}
		}
		
		return allFirmList;
		
	}
	
	
	public void cancelOrders(String executionId) {
		HashMap<String, Object> variables = (HashMap<String, Object>) runtimeService.getVariables(executionId);
		//List<FirmOrder> firmOrderList = new ArrayList<FirmOrder>();
		//firmOrderList.add(firmOrder);
		
	//	variables.put("ordersFromFirm", firmOrderList);
		int i = (int) variables.get("numberOfCancel");
		variables.put("numberOfCancel", i+1);
		System.out.println("cancel execution " + executionId + " " + variables.toString());
		//System.out.println("broj otkazivanja " + i);
		runtimeService.setVariables(executionId, variables);
	}
	

}
