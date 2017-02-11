'use strict';

describe('TimeScaleApplicationStatusLog Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTimeScaleApplicationStatusLog, MockTimeScaleApplication;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTimeScaleApplicationStatusLog = jasmine.createSpy('MockTimeScaleApplicationStatusLog');
        MockTimeScaleApplication = jasmine.createSpy('MockTimeScaleApplication');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TimeScaleApplicationStatusLog': MockTimeScaleApplicationStatusLog,
            'TimeScaleApplication': MockTimeScaleApplication
        };
        createController = function() {
            $injector.get('$controller')("TimeScaleApplicationStatusLogDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:timeScaleApplicationStatusLogUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
