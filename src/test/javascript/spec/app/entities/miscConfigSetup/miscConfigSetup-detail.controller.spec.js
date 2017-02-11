'use strict';

describe('MiscConfigSetup Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockMiscConfigSetup;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockMiscConfigSetup = jasmine.createSpy('MockMiscConfigSetup');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'MiscConfigSetup': MockMiscConfigSetup
        };
        createController = function() {
            $injector.get('$controller')("MiscConfigSetupDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:miscConfigSetupUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
