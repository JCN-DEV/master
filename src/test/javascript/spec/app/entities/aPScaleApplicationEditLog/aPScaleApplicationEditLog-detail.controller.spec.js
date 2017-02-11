'use strict';

describe('APScaleApplicationEditLog Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAPScaleApplicationEditLog, MockTimeScaleApplication;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAPScaleApplicationEditLog = jasmine.createSpy('MockAPScaleApplicationEditLog');
        MockTimeScaleApplication = jasmine.createSpy('MockTimeScaleApplication');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'APScaleApplicationEditLog': MockAPScaleApplicationEditLog,
            'TimeScaleApplication': MockTimeScaleApplication
        };
        createController = function() {
            $injector.get('$controller')("APScaleApplicationEditLogDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:aPScaleApplicationEditLogUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
