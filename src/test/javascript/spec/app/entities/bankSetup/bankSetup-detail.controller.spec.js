'use strict';

describe('BankSetup Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockBankSetup, MockUpazila;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockBankSetup = jasmine.createSpy('MockBankSetup');
        MockUpazila = jasmine.createSpy('MockUpazila');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'BankSetup': MockBankSetup,
            'Upazila': MockUpazila
        };
        createController = function() {
            $injector.get('$controller')("BankSetupDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:bankSetupUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
