'use strict';

describe('AllowanceSetup Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAllowanceSetup;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAllowanceSetup = jasmine.createSpy('MockAllowanceSetup');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AllowanceSetup': MockAllowanceSetup
        };
        createController = function() {
            $injector.get('$controller')("AllowanceSetupDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:allowanceSetupUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
