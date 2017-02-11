'use strict';

describe('BankAssign Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockBankAssign, MockUser, MockBankSetup, MockUpazila;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockBankAssign = jasmine.createSpy('MockBankAssign');
        MockUser = jasmine.createSpy('MockUser');
        MockBankSetup = jasmine.createSpy('MockBankSetup');
        MockUpazila = jasmine.createSpy('MockUpazila');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'BankAssign': MockBankAssign,
            'User': MockUser,
            'BankSetup': MockBankSetup,
            'Upazila': MockUpazila
        };
        createController = function() {
            $injector.get('$controller')("BankAssignDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:bankAssignUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
