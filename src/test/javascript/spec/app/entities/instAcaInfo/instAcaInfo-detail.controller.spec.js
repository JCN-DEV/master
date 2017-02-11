'use strict';

describe('InstAcaInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstAcaInfo, MockInstitute;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstAcaInfo = jasmine.createSpy('MockInstAcaInfo');
        MockInstitute = jasmine.createSpy('MockInstitute');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstAcaInfo': MockInstAcaInfo,
            'Institute': MockInstitute
        };
        createController = function() {
            $injector.get('$controller')("InstAcaInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instAcaInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
