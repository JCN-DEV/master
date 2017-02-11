'use strict';

describe('TimeScaleApplication Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTimeScaleApplication, MockInstEmployee;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTimeScaleApplication = jasmine.createSpy('MockTimeScaleApplication');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TimeScaleApplication': MockTimeScaleApplication,
            'InstEmployee': MockInstEmployee
        };
        createController = function() {
            $injector.get('$controller')("TimeScaleApplicationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:timeScaleApplicationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
