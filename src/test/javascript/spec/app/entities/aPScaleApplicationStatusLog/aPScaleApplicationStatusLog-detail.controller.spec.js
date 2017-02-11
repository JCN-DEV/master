'use strict';

describe('APScaleApplicationStatusLog Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAPScaleApplicationStatusLog, MockTimeScaleApplication;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAPScaleApplicationStatusLog = jasmine.createSpy('MockAPScaleApplicationStatusLog');
        MockTimeScaleApplication = jasmine.createSpy('MockTimeScaleApplication');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'APScaleApplicationStatusLog': MockAPScaleApplicationStatusLog,
            'TimeScaleApplication': MockTimeScaleApplication
        };
        createController = function() {
            $injector.get('$controller')("APScaleApplicationStatusLogDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:aPScaleApplicationStatusLogUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
