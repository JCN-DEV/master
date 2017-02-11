'use strict';

describe('TimeScaleApplicationEditLog Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTimeScaleApplicationEditLog, MockTimeScaleApplication;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTimeScaleApplicationEditLog = jasmine.createSpy('MockTimeScaleApplicationEditLog');
        MockTimeScaleApplication = jasmine.createSpy('MockTimeScaleApplication');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TimeScaleApplicationEditLog': MockTimeScaleApplicationEditLog,
            'TimeScaleApplication': MockTimeScaleApplication
        };
        createController = function() {
            $injector.get('$controller')("TimeScaleApplicationEditLogDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:timeScaleApplicationEditLogUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
