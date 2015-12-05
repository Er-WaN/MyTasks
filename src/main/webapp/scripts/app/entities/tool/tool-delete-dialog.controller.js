'use strict';

angular.module('myTasksApp')
	.controller('ToolDeleteController', function($scope, $uibModalInstance, entity, Tool) {

        $scope.tool = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Tool.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
