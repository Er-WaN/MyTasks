'use strict';

angular.module('myTasksApp')
	.controller('TaskHistoryDeleteController', function($scope, $uibModalInstance, entity, TaskHistory) {

        $scope.taskHistory = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TaskHistory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
