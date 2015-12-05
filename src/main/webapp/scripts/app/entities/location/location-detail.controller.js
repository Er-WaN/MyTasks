'use strict';

angular.module('myTasksApp')
    .controller('LocationDetailController', function ($scope, $rootScope, $stateParams, entity, Location, Task) {
        $scope.location = entity;
        $scope.load = function (id) {
            Location.get({id: id}, function(result) {
                $scope.location = result;
            });
        };
        var unsubscribe = $rootScope.$on('myTasksApp:locationUpdate', function(event, result) {
            $scope.location = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
