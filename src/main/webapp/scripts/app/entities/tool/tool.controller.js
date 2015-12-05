'use strict';

angular.module('myTasksApp')
    .controller('ToolController', function ($scope, $state, Tool) {

        $scope.tools = [];
        $scope.loadAll = function() {
            Tool.query(function(result) {
               $scope.tools = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.tool = {
                name: null,
                toBuy: null,
                price: null,
                number: null,
                comment: null,
                id: null
            };
        };
    });
