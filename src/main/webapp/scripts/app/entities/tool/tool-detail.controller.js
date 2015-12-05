'use strict';

angular.module('myTasksApp')
    .controller('ToolDetailController', function ($scope, $rootScope, $stateParams, entity, Tool, Task) {
        $scope.tool = entity;
        $scope.load = function (id) {
            Tool.get({id: id}, function(result) {
                $scope.tool = result;
            });
        };
        var unsubscribe = $rootScope.$on('myTasksApp:toolUpdate', function(event, result) {
            $scope.tool = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
