'use strict';

angular.module('myTasksApp').controller('LocationDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Location', 'Task',
        function($scope, $stateParams, $uibModalInstance, entity, Location, Task) {

        $scope.location = entity;
        $scope.tasks = Task.query();
        $scope.load = function(id) {
            Location.get({id : id}, function(result) {
                $scope.location = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('myTasksApp:locationUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.location.id != null) {
                Location.update($scope.location, onSaveSuccess, onSaveError);
            } else {
                Location.save($scope.location, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
