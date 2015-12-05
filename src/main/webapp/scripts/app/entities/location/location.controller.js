'use strict';

angular.module('myTasksApp')
    .controller('LocationController', function ($scope, $state, Location) {

        $scope.locations = [];
        $scope.loadAll = function() {
            Location.query(function(result) {
               $scope.locations = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.location = {
                name: null,
                outdoor: null,
                floor: null,
                id: null
            };
        };
    });
