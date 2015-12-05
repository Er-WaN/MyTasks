'use strict';

angular.module('myTasksApp')
    .controller('TaskHistoryDetailController', function ($scope, $rootScope, $stateParams, entity, TaskHistory, Task) {
        $scope.taskHistory = entity;
        $scope.load = function (id) {
            TaskHistory.get({id: id}, function(result) {
                $scope.taskHistory = result;
            });
        };
        var unsubscribe = $rootScope.$on('myTasksApp:taskHistoryUpdate', function(event, result) {
            $scope.taskHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
