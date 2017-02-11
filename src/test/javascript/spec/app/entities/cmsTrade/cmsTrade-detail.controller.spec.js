'use strict';

describe('CmsTrade Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCmsTrade, MockCmsCurriculum;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCmsTrade = jasmine.createSpy('MockCmsTrade');
        MockCmsCurriculum = jasmine.createSpy('MockCmsCurriculum');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'CmsTrade': MockCmsTrade,
            'CmsCurriculum': MockCmsCurriculum
        };
        createController = function() {
            $injector.get('$controller')("CmsTradeDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:cmsTradeUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
