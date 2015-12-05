'use strict';

describe('Tool Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTool, MockTask;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTool = jasmine.createSpy('MockTool');
        MockTask = jasmine.createSpy('MockTask');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Tool': MockTool,
            'Task': MockTask
        };
        createController = function() {
            $injector.get('$controller')("ToolDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'myTasksApp:toolUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
