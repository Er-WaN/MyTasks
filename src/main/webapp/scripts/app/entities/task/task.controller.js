'use strict';

angular.module('myTasksApp')
    .controller('TaskController', function ($scope, $state, Task) {

        $scope.tasks = [];
        $scope.loadAll = function() {
            Task.query(function(result) {
               $scope.tasks = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.task = {
                name: null,
                creationDate: null,
                lastEditDate: null,
                finishDate: null,
                priority: null,
                complexity: null,
                state: null,
                estimatedTime: null,
                description: null,
                comment: null,
                id: null
            };
        };
    });
