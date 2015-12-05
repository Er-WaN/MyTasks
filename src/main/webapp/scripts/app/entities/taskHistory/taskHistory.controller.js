'use strict';

angular.module('myTasksApp')
    .controller('TaskHistoryController', function ($scope, $state, TaskHistory) {

        $scope.taskHistorys = [];
        $scope.loadAll = function() {
            TaskHistory.query(function(result) {
               $scope.taskHistorys = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.taskHistory = {
                action: null,
                date: null,
                comment: null,
                spentTime: null,
                id: null
            };
        };
    });
