'use strict';

describe('IncrementSetup Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockIncrementSetup, MockPayScale;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockIncrementSetup = jasmine.createSpy('MockIncrementSetup');
        MockPayScale = jasmine.createSpy('MockPayScale');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'IncrementSetup': MockIncrementSetup,
            'PayScale': MockPayScale
        };
        createController = function() {
            $injector.get('$controller')("IncrementSetupDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:incrementSetupUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
