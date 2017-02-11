'use strict';

describe('FeeInvoice Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockFeeInvoice;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockFeeInvoice = jasmine.createSpy('MockFeeInvoice');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'FeeInvoice': MockFeeInvoice
        };
        createController = function() {
            $injector.get('$controller')("FeeInvoiceDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:feeInvoiceUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
