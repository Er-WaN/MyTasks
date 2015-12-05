'use strict';

angular.module('myTasksApp').controller('TaskDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Task', 'Tool', 'Location', 'TaskHistory', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Task, Tool, Location, TaskHistory, User) {

        $scope.task = entity;
        $scope.tools = Tool.query();
        $scope.locations = Location.query();
        $scope.taskhistorys = TaskHistory.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            Task.get({id : id}, function(result) {
                $scope.task = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('myTasksApp:taskUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.task.id != null) {
                Task.update($scope.task, onSaveSuccess, onSaveError);
            } else {
                Task.save($scope.task, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
