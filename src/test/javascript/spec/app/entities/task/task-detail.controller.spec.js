'use strict';

describe('Task Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTask, MockTool, MockLocation, MockTaskHistory, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTask = jasmine.createSpy('MockTask');
        MockTool = jasmine.createSpy('MockTool');
        MockLocation = jasmine.createSpy('MockLocation');
        MockTaskHistory = jasmine.createSpy('MockTaskHistory');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Task': MockTask,
            'Tool': MockTool,
            'Location': MockLocation,
            'TaskHistory': MockTaskHistory,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("TaskDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'myTasksApp:taskUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
