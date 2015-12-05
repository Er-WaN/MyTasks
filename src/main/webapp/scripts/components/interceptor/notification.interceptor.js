 'use strict';

angular.module('myTasksApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-myTasksApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-myTasksApp-params')});
                }
                return response;
            }
        };
    });
