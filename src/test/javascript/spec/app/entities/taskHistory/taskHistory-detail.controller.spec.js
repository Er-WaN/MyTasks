'use strict';

describe('TaskHistory Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTaskHistory, MockTask;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTaskHistory = jasmine.createSpy('MockTaskHistory');
        MockTask = jasmine.createSpy('MockTask');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TaskHistory': MockTaskHistory,
            'Task': MockTask
        };
        createController = function() {
            $injector.get('$controller')("TaskHistoryDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'myTasksApp:taskHistoryUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
