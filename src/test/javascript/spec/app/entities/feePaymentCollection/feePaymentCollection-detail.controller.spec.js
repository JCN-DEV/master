'use strict';

describe('FeePaymentCollection Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockFeePaymentCollection, MockFeePaymentTypeSetup, MockFeePaymentCategorySetup;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockFeePaymentCollection = jasmine.createSpy('MockFeePaymentCollection');
        MockFeePaymentTypeSetup = jasmine.createSpy('MockFeePaymentTypeSetup');
        MockFeePaymentCategorySetup = jasmine.createSpy('MockFeePaymentCategorySetup');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'FeePaymentCollection': MockFeePaymentCollection,
            'FeePaymentTypeSetup': MockFeePaymentTypeSetup,
            'FeePaymentCategorySetup': MockFeePaymentCategorySetup
        };
        createController = function() {
            $injector.get('$controller')("FeePaymentCollectionDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:feePaymentCollectionUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
