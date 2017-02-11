'use strict';

describe('TimeScale Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTimeScale, MockPayScale;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTimeScale = jasmine.createSpy('MockTimeScale');
        MockPayScale = jasmine.createSpy('MockPayScale');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TimeScale': MockTimeScale,
            'PayScale': MockPayScale
        };
        createController = function() {
            $injector.get('$controller')("TimeScaleDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:timeScaleUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
