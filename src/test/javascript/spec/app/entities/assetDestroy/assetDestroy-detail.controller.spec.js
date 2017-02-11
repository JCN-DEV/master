'use strict';

describe('AssetDestroy Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAssetDestroy, MockAssetDistribution, MockAssetRecord;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAssetDestroy = jasmine.createSpy('MockAssetDestroy');
        MockAssetDistribution = jasmine.createSpy('MockAssetDistribution');
        MockAssetRecord = jasmine.createSpy('MockAssetRecord');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AssetDestroy': MockAssetDestroy,
            'AssetDistribution': MockAssetDistribution,
            'AssetRecord': MockAssetRecord
        };
        createController = function() {
            $injector.get('$controller')("AssetDestroyDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:assetDestroyUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
