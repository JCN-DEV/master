'use strict';

describe('FeePaymentCategorySetup Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockFeePaymentCategorySetup;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockFeePaymentCategorySetup = jasmine.createSpy('MockFeePaymentCategorySetup');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'FeePaymentCategorySetup': MockFeePaymentCategorySetup
        };
        createController = function() {
            $injector.get('$controller')("FeePaymentCategorySetupDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:feePaymentCategorySetupUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
