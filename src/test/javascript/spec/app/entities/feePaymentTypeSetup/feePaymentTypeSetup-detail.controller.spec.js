'use strict';

describe('FeePaymentTypeSetup Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockFeePaymentTypeSetup;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockFeePaymentTypeSetup = jasmine.createSpy('MockFeePaymentTypeSetup');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'FeePaymentTypeSetup': MockFeePaymentTypeSetup
        };
        createController = function() {
            $injector.get('$controller')("FeePaymentTypeSetupDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:feePaymentTypeSetupUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
