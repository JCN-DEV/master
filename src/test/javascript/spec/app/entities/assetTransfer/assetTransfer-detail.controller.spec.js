'use strict';

describe('AssetTransfer Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAssetTransfer, MockEmployee, MockAssetRecord;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAssetTransfer = jasmine.createSpy('MockAssetTransfer');
        MockEmployee = jasmine.createSpy('MockEmployee');
        MockAssetRecord = jasmine.createSpy('MockAssetRecord');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AssetTransfer': MockAssetTransfer,
            'Employee': MockEmployee,
            'AssetRecord': MockAssetRecord
        };
        createController = function() {
            $injector.get('$controller')("AssetTransferDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:assetTransferUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
