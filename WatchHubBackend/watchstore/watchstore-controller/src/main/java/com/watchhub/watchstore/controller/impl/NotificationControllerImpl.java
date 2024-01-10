package com.watchhub.watchstore.controller.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.watchhub.watchstore.controller.NotificationController;
import com.watchhub.watchstore.dto.NotificationDto;
import com.watchhub.watchstore.entity.Notifications;
import com.watchhub.watchstore.extractor.UserDetailExtractor;
import com.watchhub.watchstore.response.ApiResponse;
import com.watchhub.watchstore.service.NotificationService;
import com.watchhub.watchstore.util.constants.Constant;

/**
 * 
 * @author mdsharif
 *
 */
@RestController
public class NotificationControllerImpl implements NotificationController {
	private NotificationService notificationService;
	private Function<Notifications, NotificationDto> transformer;
	
	private final UserDetailExtractor detailExactrator;

	private static final Logger logger = LoggerFactory.getLogger(NotificationControllerImpl.class);

	@Autowired
	public NotificationControllerImpl(NotificationService notificationService,
			Function<Notifications, NotificationDto> transformer,
			final UserDetailExtractor detailExactrator) {
		this.notificationService = notificationService;
		this.transformer = transformer;
		this.detailExactrator = detailExactrator;
	}

	/**
	 * @apiNote This API is used to save the notification for out of stock watch
	 */
	@Override
	public ResponseEntity<ApiResponse> saveNotification(String modelNumber, Authentication authentication) {
		
		UserDetails userDetails =(UserDetails) this.detailExactrator.getPrincipal();
		String currentUserEmail = userDetails.getUsername();
		logger.info(Constant.REQUEST_TO_SAVE, modelNumber);

		String saveNotification = notificationService.saveNotification(modelNumber, currentUserEmail);

		logger.info(Constant.RESPONSE_OF_SAVE, modelNumber);

		ApiResponse apiResponse = new ApiResponse(saveNotification);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
	}

	/**
	 * @apiNote This API is used to update the notification when watch comes in
	 *          stock
	 */
	@Override
	public ResponseEntity<ApiResponse> updateNotification(String modelNumber) {
		logger.info(Constant.REQUEST_TO_UPDATE, modelNumber);

		String updateNotification = notificationService.updateNotification(modelNumber);

		logger.info(Constant.REQUEST_FOR_UPDATE, modelNumber);

		ApiResponse apiResponse = new ApiResponse(updateNotification);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.ACCEPTED);
	}

	/**
	 * @apiNote This API is used to get list of all notification created by user
	 */
	@Override
	public ResponseEntity<List<NotificationDto>> getAllNotificationByUserEmailId(String emailId) {

		logger.info(Constant.REQUEST_TO_RETRIEVE, emailId);

		List<Notifications> notificationByUserId = notificationService.getNotificationByUserId(emailId);

		List<NotificationDto> NotificationDto = notificationByUserId.stream().map(transformer::apply)
				.collect(Collectors.toList());
		logger.info(Constant.RECIEVED_NOTIFICATIONS, NotificationDto.size(), emailId);
		return ResponseEntity.ok(NotificationDto);
	}

	/**
	 * @apiNote This API is used to get all the notification
	 */
	@Override
	public ResponseEntity<List<NotificationDto>> getAllNotifications() {
		List<Notifications> allNotifications = notificationService.getAllNotifications();

		logger.info(Constant.REQUEST_FOR_ALL_NOTIFICATION);
		List<NotificationDto> allNotificationsDto = allNotifications.stream().map(transformer::apply)
				.collect(Collectors.toList());

		logger.info(Constant.ALL_NOTIFICATION, allNotificationsDto.size());

		return ResponseEntity.ok(allNotificationsDto);
	}

}
