'use strict';

angular.module('myTasksApp')
    .controller('TaskDetailController', function ($scope, $rootScope, $stateParams, entity, Task, Tool, Location, TaskHistory, User) {
        $scope.task = entity;
        $scope.load = function (id) {
            Task.get({id: id}, function(result) {
                $scope.task = result;
            });
        };
        var unsubscribe = $rootScope.$on('myTasksApp:taskUpdate', function(event, result) {
            $scope.task = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
