'use strict';

describe('AssetRecord Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAssetRecord, MockAssetCategorySetup, MockAssetTypeSetup, MockAssetSupplier;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAssetRecord = jasmine.createSpy('MockAssetRecord');
        MockAssetCategorySetup = jasmine.createSpy('MockAssetCategorySetup');
        MockAssetTypeSetup = jasmine.createSpy('MockAssetTypeSetup');
        MockAssetSupplier = jasmine.createSpy('MockAssetSupplier');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AssetRecord': MockAssetRecord,
            'AssetCategorySetup': MockAssetCategorySetup,
            'AssetTypeSetup': MockAssetTypeSetup,
            'AssetSupplier': MockAssetSupplier
        };
        createController = function() {
            $injector.get('$controller')("AssetRecordDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:assetRecordUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
