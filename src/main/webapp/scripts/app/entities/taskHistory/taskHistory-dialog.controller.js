'use strict';

angular.module('myTasksApp').controller('TaskHistoryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'TaskHistory', 'Task',
        function($scope, $stateParams, $uibModalInstance, entity, TaskHistory, Task) {

        $scope.taskHistory = entity;
        $scope.tasks = Task.query();
        $scope.load = function(id) {
            TaskHistory.get({id : id}, function(result) {
                $scope.taskHistory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('myTasksApp:taskHistoryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.taskHistory.id != null) {
                TaskHistory.update($scope.taskHistory, onSaveSuccess, onSaveError);
            } else {
                TaskHistory.save($scope.taskHistory, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
