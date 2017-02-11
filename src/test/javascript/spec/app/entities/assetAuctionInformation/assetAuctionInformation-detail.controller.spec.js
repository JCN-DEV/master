'use strict';

describe('AssetAuctionInformation Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAssetAuctionInformation, MockAssetDistribution;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAssetAuctionInformation = jasmine.createSpy('MockAssetAuctionInformation');
        MockAssetDistribution = jasmine.createSpy('MockAssetDistribution');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AssetAuctionInformation': MockAssetAuctionInformation,
            'AssetDistribution': MockAssetDistribution
        };
        createController = function() {
            $injector.get('$controller')("AssetAuctionInformationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:assetAuctionInformationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
