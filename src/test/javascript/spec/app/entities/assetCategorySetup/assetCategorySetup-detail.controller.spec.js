'use strict';

describe('AssetCategorySetup Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAssetCategorySetup, MockAssetTypeSetup;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAssetCategorySetup = jasmine.createSpy('MockAssetCategorySetup');
        MockAssetTypeSetup = jasmine.createSpy('MockAssetTypeSetup');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AssetCategorySetup': MockAssetCategorySetup,
            'AssetTypeSetup': MockAssetTypeSetup
        };
        createController = function() {
            $injector.get('$controller')("AssetCategorySetupDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:assetCategorySetupUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
