'use strict';

angular.module('myTasksApp').controller('ToolDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tool', 'Task',
        function($scope, $stateParams, $uibModalInstance, entity, Tool, Task) {

        $scope.tool = entity;
        $scope.tasks = Task.query();
        $scope.load = function(id) {
            Tool.get({id : id}, function(result) {
                $scope.tool = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('myTasksApp:toolUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.tool.id != null) {
                Tool.update($scope.tool, onSaveSuccess, onSaveError);
            } else {
                Tool.save($scope.tool, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
