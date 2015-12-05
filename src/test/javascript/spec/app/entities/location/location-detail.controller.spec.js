'use strict';

describe('Location Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockLocation, MockTask;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockLocation = jasmine.createSpy('MockLocation');
        MockTask = jasmine.createSpy('MockTask');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Location': MockLocation,
            'Task': MockTask
        };
        createController = function() {
            $injector.get('$controller')("LocationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'myTasksApp:locationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
